import {Button, Col, Modal, Row, Table} from "react-bootstrap";
import React, {useEffect, useState} from "react";
import {useLocation, useNavigate} from "react-router-dom";
import {Character} from "../models/Character";
import {addCharacterToCampaign, getAllCharactersByClientId} from "../services/clientService";
import {CharacterList} from "./CharacterList";
import {getClientId} from "../security/authService";

interface Props {
    show: boolean;
    handleClose: () => void;
    characters: Character[];
    campaignId: number;
}
export const JoinCampaign = ({show, handleClose, characters, campaignId}: Props) => {
    const navigate = useNavigate();
    const location = useLocation();

    const handleclick = (charId:number) => {
        handleClose();
        addCharacterToCampaign(charId, campaignId).then((_)=> {
            if (campaignId !== undefined) navigate("/campaignpage/" + campaignId);
            window.location.reload();

        })
    }

    const characterList = characters.map(
        character => {
            return <tr key={character.id} className="align-items-baseline">
                <td>{character.name}</td>
                <td>Niveau: {character.level}</td>
                <td>Classe: {character.classe}</td>
                <td>
                    <Button variant="warning" className="text-dark" onClick={() => handleclick(character.id!)}>Ajouter</Button>
                </td>
            </tr>
        }
    );

    return (
        <Modal show={show} onHide={handleClose} className="p-2  text-warning border border-warning rounded align-content-center">
            <Modal.Header closeButton className="text-warning bg-dark border border-warning">
                <Modal.Title>Rejoindre la campagne avec:</Modal.Title>
            </Modal.Header>
            <Modal.Body className="bg-dark border border-warning">
                <Row>
                    <Col>

                        <Table className="text-warning border border-warning align-items-baseline overflow-auto">
                            <tbody>
                                {characterList}
                            </tbody>
                        </Table>

                    </Col>
                </Row>
            </Modal.Body>
            <Modal.Footer className="bg-dark border border-warning">
                <Button variant="outline-warning" className="mt-3"  >Soumettre</Button>
            </Modal.Footer>
        </Modal>

    );
}