import {useNavigate} from "react-router-dom";
import {Button, Container, Navbar} from "react-bootstrap";
import {Dashboard} from "./Dashboard";
import {getClientId, logout} from "../security/authService";
import React from "react";

export const NavBar = () => {
    const navigate = useNavigate();

    const goToDashboard = () =>{
        const id = getClientId();
        navigate("/clientpage/" + id);
    }
    const disconnect = () => {
        logout();
        navigate("/");
    };

    return (
        <Navbar className="bg-dark text-warning">
            <Container className="flex-row justify-content-between">
                <Navbar.Brand className="bg-dark text-warning" onClick={goToDashboard}>Page Principal</Navbar.Brand>
                <Button variant="outline-warning" onClick={disconnect} className="me-2">Se déconnecter</Button>
            </Container>
        </Navbar>

    );
}