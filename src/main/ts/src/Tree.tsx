import React, { useMemo, useRef, useCallback, useEffect, useState } from "react"
import { extend, ReactThreeFiber, useFrame, useLoader, useThree } from "@react-three/fiber"
import { OrbitControls } from "three/examples/jsm/controls/OrbitControls"
import * as _ from "lodash"

declare global {
  namespace JSX {
    interface IntrinsicElements {
      orbitControls: ReactThreeFiber.Object3DNode<OrbitControls, typeof OrbitControls>
    }
  }
}

extend({ OrbitControls })

class TreeProps {
  strategy: any;
}

export function Tree(props : TreeProps) {

  const [positions, setPositions] = useState<Float32Array>(undefined)
  const [colors, setColors] = useState<Float32Array>(undefined)
  const colorAttr = useRef(new Array())

  useEffect(() => {  
    var p = []
    var c = []
    fetch("/positions").then((response) => response.json()).then((value : any) => {
      for (const v of value) {
        p.push(v[0])
        p.push(v[2])
        p.push(v[1])
        c.push(0)
        c.push(0)
        c.push(0)
      }

      setPositions(new Float32Array(p))
      setColors(new Float32Array(c) )
    });
  }, [])

  useEffect(() => {  
    if (props.strategy == null) {
      return
    }
    console.log("Subscribing to event stream "+props.strategy)
    const sse = new EventSource(
      'http://localhost:8037/color-stream?strategy='+props.strategy,
      { withCredentials: false }
    );  

    var last = Date.now()
    var i = 0
    function getRealtimeData(data : any) {
      i++
      const parts = data.split(/,/)
      const loopCount = parts.shift()
      for (var idx = 0; idx < parts.length/3; idx++) {
        if (colorAttr.current[idx] == undefined) {
          return
        }
        const r = Math.ceil(255*parseFloat(parts[idx * 3]))
        const g = Math.ceil(255*parseFloat(parts[idx * 3 + 1]))
        const b = Math.ceil(255*parseFloat(parts[idx * 3 + 2]))
        colorAttr.current[idx].color.setStyle("rgb("+r+","+g+","+b+")")
        colorAttr.current[idx].color[idx * 3 + 1] = parseFloat(parts[idx*3 + 1])
        colorAttr.current[idx].color[idx * 3 + 2] = parseFloat(parts[idx*3 + 2])
        colorAttr.current[idx].needsUpdate = true;
      }
      if (Date.now() - last > 1000) {
        console.log(i+" LED updates in "+(Date.now() - last)+" milliseconds")
        last = Date.now()
        i = 0
      }
    }  
      
    sse.onmessage = e => getRealtimeData(e.data);  
    sse.onerror = () => {
      console.log("Error")
      // error log here 
      sse.close();
    }
    return () => {
      console.log("Closing")
      sse.close();
    };
  }, [props.strategy]);

  if (positions === undefined || colors === undefined) {
    return null
  }
/*
  <mesh >
  <sphereGeometry args={[0.1, 64, 64]} />
  <meshBasicMaterial color="orange" />
</mesh>
*/



  return (
    <>
      {
        _.range(0, positions.length/3).map(idx => {
            return (
              <mesh key={'mesh'+idx} castShadow position={[positions[3*idx], positions[3*idx+1], positions[3*idx+2]]}>
                <sphereGeometry args={[0.01, 32, 32]} attach="geometry">
                  <bufferAttribute attach="position" count={positions.length / 3} array={positions} itemSize={3} />
                  {false && <bufferAttribute ref={(el) => colorAttr.current[idx] = el} attach="attributes-color" count={colors.length / 3} array={colors} itemSize={3} />}
                </sphereGeometry>
                <meshBasicMaterial ref={(el) => colorAttr.current[idx] = el} color="white" />
              </mesh>
            )
          })
      }
    </>
  )
}

export function Controls(props: any) {
  const controls = useRef<OrbitControls>()
  const { camera, gl } = useThree()
  useFrame(() => controls.current.update())
  //
  //
  return <orbitControls ref={controls} args={[camera, gl.domElement]}  maxDistance={1} enableDamping dampingFactor={0.1} rotateSpeed={0.5}/>
}
