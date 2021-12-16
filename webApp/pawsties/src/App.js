import logo from './logo.svg';
import './App.css';
import RescListMascotas from './Components/rescatista/RescListMascota';
import RescatistaLogin from './Components/rescatista/RescatistaLogin';
import EditarMascota from './Components/rescatista/EditarMascota';
import React from 'react';

import {
  BrowserRouter as Router,
  Link,
  Switch,
  Route
} from 'react-router-dom'; 

import 'bootstrap/dist/css/bootstrap.min.css';
import { Container, Navbar, NavItem } from 'reactstrap';

function App(props) {
  return (
    
    <Router>
      <Container>
        <Navbar expand="lg" className="navheader">
          <div className="collapse navbar-collapse">
            <ul className='navbar-nav mr-auto'>
              <NavItem>
                <Link to={'/RescListMascota'} className='nav-link'>Rescatista</Link>
              </NavItem>
              <NavItem>
                Adoptante
              </NavItem>
            </ul>
          </div>
        </Navbar>
      </Container>
      <br />
      <Switch>
        <Route exact path='/RescListMascota' component={RescListMascotas} />
        <Route exact path='/RescatistaLogin' component={RescatistaLogin} />
        <Route exact path='/editpet/:id' component={EditarMascota} />
      </Switch>
    </Router>
  );
}

export default App;
