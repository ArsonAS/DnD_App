import React from 'react';
import './App.css';
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import {Signup} from "./components/Autentication/Signup";
import {Login} from "./components/Autentication/Login";
import {Dashboard} from "./components/Dashboard";

function App() {
  return (
      <Router>

          <Routes>
              <Route path="/" element={<Login/>}/>
              <Route path="/signup" element={<Signup/>}/>
              <Route path="/clientpage:id" element={<Dashboard/>}/>
          </Routes>
      </Router>
  );
}

export default App;
