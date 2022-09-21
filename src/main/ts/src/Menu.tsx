import React, { useEffect, useState } from 'react'
import * as _ from "lodash"
import Navbar from 'react-bootstrap/Navbar'
import NavDropdown from 'react-bootstrap/NavDropdown'
import { Badge } from 'react-bootstrap';


class MenuProps {
  online: boolean;
  strategy: any;
  onChange: (any) => void;
}

function Menu(props : MenuProps) {

  const [strategies, setStrategies] = useState({})

  useEffect(() => {  
    fetch("/strategies").then((response) => response.json()).then((value : any) => {
      setStrategies(value)
      if (props.strategy == null) {
        props.onChange(_.sortBy(value, "name")[0]["class"])
      }
    });
  }, [])

const current : any = _.find(strategies, (s) => s["class"] == props.strategy)
  return (
    <div>
      <Navbar variant="pills" onSelect={e => props.onChange(e)}>
        <Navbar.Brand>TreeD</Navbar.Brand>
        <NavDropdown title={current != undefined ? (current.name+" ("+current.author+")") : 'Strategie'} id="nav-dropdown">
          {
            _.map(_.sortBy(strategies, "name"), (v) => {
                return (
                  <NavDropdown.Item active={v['class'] == props.strategy} key={v['class']} eventKey={v['class']}>{v.name} ({v.author})</NavDropdown.Item>
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
