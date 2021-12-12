import logo from './logo.svg';
import './App.css';
import React from 'react';

class Component1 extends React.Component {
  render () {
    return (<h1>Component 1</h1>);
  }
}

class Component2 extends React.Component {
  render() {
    return (<h1>Component 2</h1>);
  }
}

class App extends React.Component {
  render () {
    return (<Component1 />);
  }
}
/*function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}*/

export default App;
