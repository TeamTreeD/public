import React, { useMemo, useRef, useCallback, useEffect, useState } from "react"
import { extend, ReactThreeFiber, useFrame, useLoader, useThree } from "@react-three/fiber"
import { OrbitControls } from "three/examples/jsm/controls/OrbitControls"
import { TextureLoader } from "three"
import * as _ from "lodash"

declare global {
  namespace JSX {
    interface IntrinsicElements {
      orbitControls: ReactThreeFiber.Object3DNode<OrbitControls, typeof OrbitControls>
    }
  }
}

extend({ OrbitControls })

const vertexShader = `
precision highp float;
		uniform mat4 modelViewMatrix;
		uniform mat4 projectionMatrix;
		uniform float time;

		attribute vec3 position;
		attribute vec2 uv;
		attribute vec3 translate;

		varying vec2 vUv;
		varying float vScale;

		void main() {

			vec4 mvPosition = modelViewMatrix * vec4( translate, 1.0 );
			vec3 trTime = vec3(translate.x + time,translate.y + time,translate.z + time);
			float scale =  sin( trTime.x * 2.1 ) + sin( trTime.y * 3.2 ) + sin( trTime.z * 4.3 );
			vScale = scale;
			scale = scale * 10.0 + 10.0;
			mvPosition.xyz += position * scale;
			vUv = uv;
			gl_Position = projectionMatrix * mvPosition;

		}
`

const fragmentShader=`
precision highp float;

		uniform sampler2D map;

		varying vec2 vUv;
		varying float vScale;

		// HSL to RGB Convertion helpers
		vec3 HUEtoRGB(float H){
			H = mod(H,1.0);
			float R = abs(H * 6.0 - 3.0) - 1.0;
			float G = 2.0 - abs(H * 6.0 - 2.0);
			float B = 2.0 - abs(H * 6.0 - 4.0);
			return clamp(vec3(R,G,B),0.0,1.0);
		}

		vec3 HSLtoRGB(vec3 HSL){
			vec3 RGB = HUEtoRGB(HSL.x);
			float C = (1.0 - abs(2.0 * HSL.z - 1.0)) * HSL.y;
			return (RGB - 0.5) * C + HSL.z;
		}

		void main() {
			vec4 diffuseColor = texture2D( map, vUv );
			gl_FragColor = vec4( diffuseColor.xyz * HSLtoRGB(vec3(vScale/5.0, 1.0, 0.5)), diffuseColor.w );

			if ( diffuseColor.w < 0.5 ) discard;
		}
`


export function Tree() {

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
    console.log("Subscribing to event stream")
    const sse = new EventSource(
      'http://localhost:8080/color-stream',
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
  }, []);

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
  return <orbitControls ref={controls} args={[camera, gl.domElement]} enableDamping dampingFactor={0.1} rotateSpeed={0.5}/>
}
