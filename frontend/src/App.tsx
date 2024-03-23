import React from 'react';
import './App.css';
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import {Signup} from "./components/Autentication/Signup";
import {Login} from "./components/Autentication/Login";
import {Dashboard} from "./components/Dashboard";
import {CharacterPage} from "./components/CharacterPage";

function App() {
  return (
      <Router>

          <Routes>
              <Route path="/" element={<Login/>}/>
              <Route path="/signup" element={<Signup/>}/>
              <Route path="/clientpage/:id" element={<Dashboard/>}/>
              <Route path="/characterpage/:id" element={<CharacterPage/>}/>
          </Routes>
      </Router>
  );
}

export default App;
