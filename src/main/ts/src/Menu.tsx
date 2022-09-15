import React, { useEffect, useState } from 'react';
import * as _ from "lodash";
import { Container, Dropdown, Nav, Navbar, NavbarBrand, NavDropdown } from 'react-bootstrap';

class MenuProps {
  strategy: any;
  onChange: (any) => void;
}

function Menu(props : MenuProps) {

  const [strategies, setStrategies] = useState({})

  useEffect(() => {  
    fetch("/strategies").then((response) => response.json()).then((value : any) => {
      setStrategies(value)
      if (props.strategy == null) {
        props.onChange(_.keys(value)[0])
      }
    });
  }, [])


  const current = _.map(
    _.find(strategies, (v, k) => k == props.strategy), 
    (v, k) => v)

  return (
    <div>
      <Navbar variant="pills" onSelect={e => props.onChange(e)}>
        <NavbarBrand>TreeD</NavbarBrand>
        <NavDropdown title={current || 'Strategie'} id="nav-dropdown">
          {
            _.map(strategies, (v, k) => {
                return (
                  <NavDropdown.Item active={k == props.strategy} key={k} eventKey={k}>{v}</NavDropdown.Item>
                )
            })
          }
        </NavDropdown>
      </Navbar>
    </div>
  );
}

export default Menu;
