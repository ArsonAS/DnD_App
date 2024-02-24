import React from 'react';
import './App.css';
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import {Signup} from "./components/Autentication/Signup";

function App() {
  return (
      <Router>
          <Signup/>
          <Routes></Routes>
      </Router>
  );
}

export default App;
