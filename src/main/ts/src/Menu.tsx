import React, { useEffect, useState } from 'react'
import * as _ from "lodash"
import Navbar from 'react-bootstrap/Navbar'
import NavDropdown from 'react-bootstrap/NavDropdown'
import { Badge } from 'react-bootstrap';
import { StrategyStatus } from './StrategyStatus';


class MenuProps {
  online: boolean;
  strategy: any;
  setStrategyStatus: (StrategyStatus) => void;
  onChange: (any) => void;
}

function Menu(props : MenuProps) {

  const [strategies, setStrategies] = useState<any[]>([])

  useEffect(() => {  
    fetch("/strategies").then((response) => response.json()).then((value : any) => {
      setStrategies(value)
      if (props.strategy == null) {
        props.onChange(_.sortBy(value, "name")[0])
      }
    });
  }, [])

  function strategyName(s : any) {
    return s.name+(s.author ? " ("+s.author+")" : "")
  }

  const select = (s) => {
    // Get strate
    // Find strategy
    const strategy : any = _.find(strategies, (v) => v["class"] == s)
    if (strategy.enabled == "false") {
      props.setStrategyStatus(StrategyStatus.INCOMPLETE_STRATEGY)
    }
    props.onChange(strategy)
  }

  var builtinStrategies = _.filter(strategies, 
    s => s.author == "Steffen Gemkow" || s.author == "Falk Hartmann"
  )
  var extraStrategies = _.filter(strategies, 
    s => s.author != "Steffen Gemkow" && s.author != "Falk Hartmann"
  )

  return (
    <div>
      <Navbar variant="pills" onSelect={select}>
        <Navbar.Brand>TreeD</Navbar.Brand>
        <NavDropdown title={props.strategy != undefined ? strategyName(props.strategy) : 'Strategie'} id="nav-dropdown">
          {
            _.map(_.sortBy(extraStrategies, "name"), (v) => {
                return (
                  <NavDropdown.Item active={v['class'] == props.strategy} key={v['class']} eventKey={v['class']}>{strategyName(v)}</NavDropdown.Item>
                )
            })
          }
          <NavDropdown.Divider />
          {
            _.map(_.sortBy(builtinStrategies, "name"), (v) => {
                return (
                  <NavDropdown.Item active={v['class'] == props.strategy} key={v['class']} eventKey={v['class']}>{strategyName(v)}</NavDropdown.Item>
                )
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
