import React, { useEffect, useState } from 'react'
import * as _ from "lodash"
import Navbar from 'react-bootstrap/Navbar'
import NavDropdown from 'react-bootstrap/NavDropdown'
import { Badge } from 'react-bootstrap';
import { StrategyStatus } from './StrategyStatus';
import { useSearchParams } from 'react-router-dom';

class MenuProps {
  onChange: (any) => void;
  online: boolean;
  reconnectCount: number;
  strategy: any;
  setStrategyStatus: (StrategyStatus) => void;
}

function Menu(props : MenuProps) {

  const [strategies, setStrategies] = useState<any[]>([])
  const [searchParams, setSearchParams] = useSearchParams()

  useEffect(() => {  
    fetch("/strategies").then((response) => response.json()).then((value : any) => {
      const sortedStrategies = _.sortBy(value, ["author", "name"])
      setStrategies(sortedStrategies)
    });
  }, [props.reconnectCount])

  function selectStrategy(strategies, selectedStrategyName) {
    if (strategies.length == 0) {
      // No strategies yet, nothing to do.
      return
    }

    if (selectedStrategyName == null) {
      // Select the first one.
      select(strategies[0]["class"])
    } else {
      // Find and select.
      var s = _.find(strategies, s => s["class"] == selectedStrategyName)
      if (s == null) {
        s = strategies[0]
      }
      select(s["class"])
    }
  }

  useEffect(() => {
    selectStrategy(strategies, searchParams.get("strategy"))
  }, [strategies])

  useEffect(() => {
    selectStrategy(strategies, searchParams.get("strategy"))
  }, [searchParams.get("strategy")])

  function strategyName(s : any) {
    return s.name+(s.author ? " ("+s.author+")" : "")
  }

  const select = (s) => {
    // Find strategy
    const strategy : any = _.find(strategies, (v) => v["class"] == s)
    if (strategy.enabled == "false") {
      props.setStrategyStatus(StrategyStatus.INCOMPLETE_STRATEGY)
    }
    // Record selected strategy in URL.
    setSearchParams({strategy: s})
    props.onChange(strategy)
  }

  return (
    <div>
      <Navbar variant="pills" onSelect={select}>
        <Navbar.Brand>TreeD</Navbar.Brand>
        <NavDropdown title={props.strategy != undefined ? strategyName(props.strategy) : 'Strategie'} id="nav-dropdown">
          {
            _.map(_.zip(strategies, _.tail(strategies)), (v) => {
                const current = v[0]
                const next = v[1]
                return (<React.Fragment key={current['class']} >
                  <NavDropdown.Item active={current['class'] == props.strategy} eventKey={current['class']}>{strategyName(current)}</NavDropdown.Item>
                  {
                    next != undefined && current["author"] != next["author"] && 
                    <NavDropdown.Divider/>
                  }
                </React.Fragment>)
            })
          }
        </NavDropdown>
        <Navbar.Collapse className="justify-content-end">
          <Navbar.Text>
              {
                props.online &&
                <h3><Badge bg="success">Online</Badge></h3>
              }
              {
                !props.online && 
                <h3><Badge bg="danger">Offline</Badge></h3>
              }
          </Navbar.Text>
        </Navbar.Collapse>
      </Navbar>
    </div>
  );
}

export default Menu;
