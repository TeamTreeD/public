import React, { useEffect, useState } from 'react';
import './App.css';
import { Canvas } from '@react-three/fiber'
import { Controls, Tree } from './Tree';
import Menu from './Menu';
import { StrategyStatus } from './StrategyStatus';
import StatusInformation from './StatusInformation';
import { Card } from 'react-bootstrap';

function App() {

  const [strategy, setStrategy] = useState<any>()
  const [strategyStatus, setStrategyStatus] = useState<StrategyStatus>(StrategyStatus.INITIALIZING)
  const [backendAvailable, setBackendAvailable] = useState(true)

  useEffect(() => {
    const interval = setInterval(() => {
      fetch("/index.html", {method: 'HEAD'})
        .then((e) => {
          setBackendAvailable(e.status == 200)
        })
        .catch(() => {
          setBackendAvailable(false)
        })
    }, 1000);

  
    return () => clearInterval(interval); // This represents the unmount function, in which you need to clear your interval to prevent memory leaks.
  }, [])

  return (
    <div className="App">
      <Menu strategy={strategy} setStrategyStatus={setStrategyStatus} onChange={(v) => setStrategy(v)} online={backendAvailable}></Menu>
        {
          backendAvailable && 
          <>
            <StatusInformation strategyStatus={strategyStatus}/>          
            <Canvas orthographic camera={{ position: [-0.8, 1, 1], zoom: 300 }} raycaster={{ params: { Points: { threshold: 0.2 } } }}>
              <Tree strategy={strategy} strategyStatus={strategyStatus} setStrategyStatus={setStrategyStatus}/>
              {
                strategyStatus == StrategyStatus.STRATEGY_RUNNING &&
                <>
                  <Controls />
                  <axesHelper args={[10]}/>
                </>
              }
            </Canvas>
          </>
        }
        {
          !backendAvailable && 
          <Card className="defaultCard" body>
            We cannot connect to the backend. If you've started this UI 
            from the Entwickledheld platform, this most probably means that 
            you need to re-run your tests (which, in turn, will restart the
            backend of this simulator).
          </Card>
        }
    </div>
  );
}

export default App;
