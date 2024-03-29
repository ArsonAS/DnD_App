import React, {useEffect, useState} from "react";
import {Client} from "../models/Client";
import {Link, useLocation, useNavigate, useParams} from "react-router-dom";
import {Button, ButtonGroup, Col, Container, FormCheck, FormControl, Row} from "react-bootstrap";
import Form from 'react-bootstrap/Form';
import {getAllCharactersByClientId, getClientById, updateClientRole} from "../services/clientService";
import {getClientId, logout} from "../security/authService";
import {NavBar} from "./NavBar";
import {Character} from "../models/Character";
import {CharacterList} from "./CharacterList";
import FormInput from "./FormInput";
import Switch from "react-bootstrap-switch";

export const Dashboard = () => {
    const [client, setClient] = useState<Client>();
    const [characters, setCharacters] = useState<Character[]>([]);
    const [isSwitchOn, setIsSwitchOn] = useState(false);
    const navigate = useNavigate();
    const location = useLocation();
    const params = useParams();

    useEffect(() => {
        if (client !== undefined) return;
        const id = getClientId()

        getClientById(parseInt(id!)).then((response) => {
            setClient(response.data);
        })

    }, [client]);

    useEffect(() => {
        if (client === undefined) return;

        getAllCharactersByClientId(client.id!).then((response) => {
            setCharacters(response.data);
        })
    }, [client, setCharacters]);

    const goToCreateCharacter = () => {
        navigate("/character/new");
    }
    const onSwitchAction = () => {
        const id = getClientId()
        updateClientRole(parseInt(id!)).then(() => {
            window.location.reload();
        });
    };

    return (
        <Container fluid className="p-2 bg-dark vh-100 text-warning">
            <Row className="px-3 sticky-top mb-5 py-2 border border-bottom-2 border-left-0 border-right-0 border-warning align-items-baseline">
                <Col sm={{span: 3, offset: 0}} className="bg-dark px-2 py-1 align-items-baseline">
                    <p>
                        <span className="h3">{client?.username}</span> : <span className="h5">{client?.role}</span>
                    </p>
                </Col>
                <Col sm={{span: 5, offset: 0}} className="bg-dark px-2 py-1">
                    <Row className="px-2 flex-row justify-content-around">
                        {client?.role === "DM"
                            ?
                            <ButtonGroup>
                                <Button variant="warning">Créer une campaign</Button>
                            </ButtonGroup>
                            :
                            <ButtonGroup>
                                <Button variant="warning" onClick={goToCreateCharacter}>Créer un personnage</Button>
                            </ButtonGroup>
                        }
                    </Row>
                </Col>
                <Col sm={{span: 3, offset: 0}} className="bg-dark align-self-end">
                    <NavBar/>
                </Col>
            </Row>
            <Row className="px-3 mb-5">
                {client?.role === "DM"
                    ?
                    <Button variant="warning" onClick={onSwitchAction}>Voir la page en tant que joueur</Button>
                    :
                    <Button variant="warning" onClick={onSwitchAction}>Voir la page en tant que MD</Button>
                }
            </Row>
            <Col sm={{span: 5, offset: 0}}>
                <CharacterList characters={characters}/>
            </Col>


        </Container>
    );



}