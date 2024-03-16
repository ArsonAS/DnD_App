import React, {useEffect, useState} from "react";
import {Client} from "../models/Client";
import {Link, useLocation, useNavigate, useParams} from "react-router-dom";
import {Button, ButtonGroup, Col, Container, Form, FormControl, Row} from "react-bootstrap";
import FormInput from "./FormInput";
import {getClientById} from "../services/clientService";
import {getClientId, logout} from "../security/authService";

export const Dashboard = () => {
    const [client, setClient] = useState<Client>();
    const navigate = useNavigate();
    const location = useLocation();
    const params = useParams();

    useEffect(() => {
        if (client !== undefined) return;

        const id = getClientId()

        getClientById(parseInt(id!)).then((response) => {
            setClient(response.data);
        })
    });
    const disconnect = () => {
        logout();
        navigate("/");
    };


    return (
        <Container fluid className="p-2 bg-dark vh-100 text-warning">
            <h2>{client?.username}</h2>
            <div className="float-right">
                {client?.currentRole === "DM"
                    ?
                        <ButtonGroup>
                            <Button>Créer une campaign</Button>
                            <Button>Créer un PNJ</Button>
                        </ButtonGroup>
                    :
                        <ButtonGroup>
                            <Button>Créer un personnage</Button>
                        </ButtonGroup>
                }
                <Button variant="outline-warning" onClick={disconnect} className="me-2">Se déconnecter</Button>
            </div>
        </Container>
    );



}