import {Badge, Button, ButtonGroup, Col, Container, Form, FormControl, Row, Stack} from "react-bootstrap";
import {useEffect, useState} from "react";
import {Character} from "../models/Character";
import {useLocation, useNavigate, useParams} from "react-router-dom";
import {getCharacterById, getClientById} from "../services/clientService";
import {NavBar} from "./NavBar";

export const CharacterPage = () => {
    const [character, setCharacter] = useState<Character>();
    const navigate = useNavigate();
    const location = useLocation();
    const params = useParams();

    useEffect(() => {
        if (character !== undefined) return;
        const id = parseInt(params.id!);


        getCharacterById(id).then((response) => {
            setCharacter(response.data);
        })

    },[setCharacter]);


    return (
        <>
            <Container fluid className="p-3 bg-dark vh-100 text-warning border border-warning">
                <Row className="px-3 fixed-top">
                    <Col sm={{span: 12, offset: 0}} className="bg-dark bg-gradient border border-warning ">
                        <NavBar/>
                    </Col>
                    <Col sm={{span: 12, offset: 0}}>
                        <Row>
                            <Col sm={{span: 3, offset: 0}} className="bg-dark bg-gradient border border-warning px-2 py-1">
                                <h4>{character?.name}</h4>
                            </Col>
                            <Col className="bg-dark bg-gradient border border-warning px-2 py-2">
                                <Row className="px-2 flex-row justify-content-around">
                                    <h5>Race: {character?.race}</h5>
                                    <h5>Classe: {character?.classe}</h5>
                                    <h5>Niveau: {character?.level}</h5>
                                </Row>
                            </Col>
                        </Row>
                    </Col>

                    <Col sm={{span: 3, offset: 0}} className="flex-column border border-warning vh-100 overflow-auto">
                        <p>Acrobaties</p>
                        <p>Manipulation des animaux</p>
                        <p>Arcanes</p>
                        <p>Athlétisme</p>
                        <p>Tromperie</p>
                        <p>Histoire</p>
                        <p>Aperçu</p>
                        <p>Intimidation</p>
                        <p>Investigation</p>
                        <p>Médecine</p>
                        <p>Nature</p>
                        <p>Perception</p>
                        <p>Performance</p>
                        <p>Persuasion</p>
                        <p>Religion</p>
                        <p>Tour de passe-passe</p>
                        <p>Furtivité</p>
                        <p>Survivance</p>

                    </Col>
                    <Col sm={{span: 9, offset: 0}} className="bg-dark border border-warning p-2 align-self-sm-start">
                        <Row className="p-3 flex-row justify-content-between">
                            <Badge pill bg="danger" className="pt-3 border border-warning">
                                <p className="align-items-center">
                                    <span className="h6 d-none d-lg-inline-block">Force:</span>
                                    <span className="h6 d-lg-none">For:</span>
                                    <span className="d-inline-block"><u><strong>+0 </strong></u><small>({character?.characterAbilityScores.strength})</small></span>
                                </p>
                            </Badge>
                            <Badge pill bg="secondary" className="pt-3 border border-warning">
                                <p className="align-items-center">
                                    <span className="h6 d-none d-lg-inline-block">Dextérité:</span>
                                    <span className="h6 d-lg-none">Dex: </span>
                                    <span className="d-inline-block"><u><strong>+1 </strong></u><small>({character?.characterAbilityScores.dexterity})</small> </span>
                                </p>
                            </Badge>
                            <Badge pill bg="dark" className="pt-3 border border-warning">
                                <p className="align-items-center">
                                    <span className="h6 d-none d-lg-inline-block">Constitution:</span>
                                    <span className="h6 d-lg-none">Con: </span>
                                    <span className="d-inline-block"><u><strong>+2 </strong></u><small>({character?.characterAbilityScores.constitution})</small></span>
                                </p>
                            </Badge>
                            <Badge pill bg="info" className="pt-3 border border-warning">
                                <p className="align-items-center">
                                    <span className="h6 d-none d-lg-inline-block">Intelligence:</span>
                                    <span className="h6 d-lg-none">Int: </span>
                                    <span className="d-inline-block"><u><strong>+2 </strong></u><small>({character?.characterAbilityScores.intelligence})</small></span>
                                </p>
                            </Badge>
                            <Badge pill bg="primary" className="pt-3 border border-warning">
                                <p className="align-items-center">
                                    <span className="h6 d-none d-lg-inline-block">Sagesse:</span>
                                    <span className="h6 d-lg-none">Sag: </span>
                                    <span className="d-inline-block"><u><strong>+0 </strong></u><small>({character?.characterAbilityScores.wisdom})</small></span>
                                </p>
                            </Badge>
                            <Badge pill bg="success" className="pt-3 border border-warning">
                                <p className="align-items-center">
                                    <span className="h6 d-none d-lg-inline-block">Charisme:</span>
                                    <span className="h6 d-lg-none">Cha:</span>
                                    <span className="d-inline-block"><u><strong>+2 </strong></u><small>({character?.characterAbilityScores.charisma})</small></span>
                                </p>
                            </Badge>
                        </Row>
                    </Col>
                </Row>


                <Row>
                    <Col>

                    </Col>
                </Row>


            </Container>
        </>

    );
}