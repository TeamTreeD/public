import React, { useEffect, useState } from 'react';
import './App.css';
import { Canvas } from '@react-three/fiber'
import { Controls, Tree } from './Tree';
import Menu from './Menu';

function App() {

  const [strategy, setStrategy] = useState<any>()

  return (
    <div className="App">
      <Menu strategy={strategy} onChange={(v) => setStrategy(v)}></Menu>
      <Canvas orthographic camera={{ position: [-0.8, 1, 1], zoom: 300 }} raycaster={{ params: { Points: { threshold: 0.2 } } }}>
        <Tree strategy={strategy}/>
        <Controls />
        <axesHelper args={[10]}/>
      </Canvas>
    </div>
  );
}

export default App;
