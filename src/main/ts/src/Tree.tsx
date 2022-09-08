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
  afterReconnect: () => void;
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

  const [runningReported, setRunningReported] = useState(false)

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
    if (props.strategy == null || props.strategy.enabled == "false") {
      unsubscribe()
      return
    }
    subscribe()
  }, [props.strategy])

  useEffect(() => {
    if (runningReported) {
      props.afterReconnect()
      props.setStrategyStatus(StrategyStatus.STRATEGY_RUNNING)
    } else {
      props.setStrategyStatus(StrategyStatus.STRATEGY_ERROR)
    }
  }, [runningReported])

  function unsubscribe() {
    if (eventSource != undefined) {
      eventSource.close()
      setEventSource(undefined)
    }
  }

  function subscribe() {
    unsubscribe()
    console.log("Subscribing to event stream "+props.strategy["class"])
    var newSource = new EventSource(
      // As the npm proxy buffers SSE, we have to go directly to the CORS-disabled spring boot port...
      window.location.href.replace(/:8000.+$/, ":8038").replace(/[/]?[?].+/, "")+'/color-stream?strategy='+props.strategy["class"],
      { withCredentials: false }
    )
    
    newSource.onmessage = (e) => {
      setRunningReported(true)
      processMessage(e.data);  
    } 
    newSource.onerror = (e) => {
      setRunningReported(false)
      newSource.close();
      setEventSource(null)
      // Set a retry handler.
      setTimeout(() => {
        subscribe()
      }, 5000)
    }
    setEventSource(newSource)
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
