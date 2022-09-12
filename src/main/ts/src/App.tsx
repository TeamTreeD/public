import React, { useEffect } from 'react';
import './App.css';
import { Canvas } from '@react-three/fiber'
import { Controls, Tree } from './Tree';

function App() {
  return (
    <div className="App">
      <Canvas orthographic camera={{ position: [0, 1, 1], zoom: 60 }} raycaster={{ params: { Points: { threshold: 0.2 } } }}>
        <Tree />
        <Controls />
        <axesHelper args={[10]}/>
      </Canvas>
    </div>
  );
}

export default App;
