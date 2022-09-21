import React, { useMemo, useRef, useCallback, useEffect, useState } from "react"
import { extend, ReactThreeFiber, useFrame, useLoader, useThree } from "@react-three/fiber"
import { OrbitControls } from "three/examples/jsm/controls/OrbitControls"
import * as _ from "lodash"
import { StrategyStatus } from "./StrategyStatus"

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
  strategyStatus: StrategyStatus;
  setStrategyStatus: (StrategyStatus) => void;
}

export function Tree(props : TreeProps) {

  const [positions, setPositions] = useState<Float32Array>(undefined)
  const [colors, setColors] = useState<Float32Array>(undefined)
  const colorAttr = useRef(new Array())
  const [eventSource, setEventSource] = useState<EventSource>(undefined)
  var last = Date.now()
  var i = 0

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

  function processMessage(data : any) {
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
//        console.log(i+" LED updates in "+(Date.now() - last)+" milliseconds")
      last = Date.now()
      i = 0
    }
  }  

  useEffect(() => {  
    if (props.strategy == null) {
      return
    }
    subscribe()
  }, [props.strategy]);

  function subscribe() {
    if (eventSource != undefined) {
      console.log("Closing current stream");
      eventSource.close()
      setEventSource(undefined)
    }

    console.log("Subscribing to event stream "+props.strategy)
    var newSource = new EventSource(
      // As the npm proxy buffers SSE, we have to go directly to the CORS-disabled spring boot port...
      window.location.href.replace(":8000", ":8038")+'color-stream?strategy='+props.strategy,
      { withCredentials: false }
    )
      
    newSource.onmessage = (e) => {
      props.setStrategyStatus(StrategyStatus.STRATEGY_RUNNING)
      processMessage(e.data);  
    } 
    newSource.onerror = () => {
      props.setStrategyStatus(StrategyStatus.STRATEGY_ERROR)
      newSource.close();
      setEventSource(null)
      // Set a retry handler.
      setTimeout(() => {
        subscribe()
      }, 120000)
    }
    setEventSource(newSource)

/*    return () => {
      console.log("Closing")
      eventSource.close();
    };*/
  }

  if (positions === undefined || colors === undefined) {
    return null
  }
  if (props.strategyStatus == StrategyStatus.STRATEGY_RUNNING) {
    return (<>
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
      </>) 
  } else {
    return (<></>)
  }
}

export function Controls(props: any) {
  const controls = useRef<OrbitControls>()
  const { camera, gl } = useThree()
  useFrame(() => controls.current.update())
  //
  //
  return <orbitControls ref={controls} args={[camera, gl.domElement]}  maxDistance={1} enableDamping dampingFactor={0.1} rotateSpeed={0.5}/>
}
