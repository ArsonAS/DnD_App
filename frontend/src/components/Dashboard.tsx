import React, {useEffect, useState} from "react";
import {Client} from "../models/Client";
import {Link, useLocation, useNavigate, useParams} from "react-router-dom";
import {Button, ButtonGroup, Col, Container, Form, FormControl, Row} from "react-bootstrap";
import {getClientById} from "../services/clientService";
import {getClientId, logout} from "../security/authService";
import {NavBar} from "./NavBar";
import {CHARACTER_PREFIX} from "../constants/apiPrefixes";

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

    const goToCreateCharacter = () => {
        navigate("/character/new");
    }

    return (
        <Container fluid className="p-2 bg-dark vh-100 text-warning">
            <Row className="px-3 fixed-top mb-5 py-2">
                <Col sm={{span: 3, offset: 0}} className="bg-dark px-2 py-1 align-self-sm-start">
                    <h2>{client?.username}</h2>
                </Col>
                <Col sm={{span: 6, offset: 0}} className="bg-dark px-2 py-1">
                    <Row className="px-2 flex-row justify-content-around">
                        {client?.role === "DM"
                            ?
                            <ButtonGroup>
                                <Button variant="warning">Créer une campaign</Button>
                                <Button>Créer un PNJ</Button>
                            </ButtonGroup>
                            :
                            <ButtonGroup>
                                <Button variant="warning" onClick={goToCreateCharacter}>Créer un personnage</Button>

                            </ButtonGroup>
                        }
                    </Row>
                </Col>
                <Col sm={{span: 3, offset: 0}} className="bg-dark align-self-sm-end">
                    <NavBar/>
                </Col>


            </Row>



        </Container>
    );



}