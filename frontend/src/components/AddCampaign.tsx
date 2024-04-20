import {useNavigate} from "react-router-dom";
import React, {useState} from "react";
import {Button, Col, Modal, Row} from "react-bootstrap";
import FormInput from "./FormInput";
import {createCampaign} from "../services/clientService";
import {Campaign} from "../models/Campaign";

interface Props {
    show: boolean;
    handleClose: () => void;
    clientId: number;
}
export const AddCampaign = ({show, handleClose, clientId}: Props) => {
    const navigate = useNavigate();

    const [name, setName] = useState<string>("");
    const [notes, setNotes] = useState<string>("");
    const [finished, setIsFinished] = useState<boolean>(false);
    const [errors, setErrors] = useState<string[]>([]);

    const validateForm = (): boolean => {
        let isFormValid = true;
        let errorsToDisplay: string[] = [];

        if (name === "") {
            errorsToDisplay.push("Le Nom de la campagne ne peut pas être vide");
            isFormValid = false;
        }
        setErrors(errorsToDisplay);
        return isFormValid;
    };


    const submitForm = () => {
        if (!validateForm()) return;
        let campaign: Campaign = {
            name,
            notes,
            finished,
            clientId,
        };
        handleSubmit(campaign, clientId);
    };
    const handleSubmit = (campaign: Campaign, clientId: number) => {
        handleClose();
        createCampaign(campaign, clientId).then(() => {
            navigate("/clientpage/" + clientId);
            window.location.reload();
        })
    }
    return (
        <Modal show={show} onHide={handleClose} className="p-2  text-warning border border-warning rounded align-content-center">
            <Modal.Header closeButton className="text-warning bg-dark border border-warning">
                <Modal.Title>Nouvelle campagne</Modal.Title>
            </Modal.Header>
            <Modal.Body className="bg-dark border border-warning">
                <Row>
                    <Col>
                        <FormInput label="Nom de la campagne" value={name} onChange={(e)=> setName(e.target.value)}
                                   errors={errors} formError="Le Nom de la campagne ne peut pas être vide"
                                   controlId="campaignName"
                        />
                    </Col>
                </Row>
            </Modal.Body>
            <Modal.Footer className="bg-dark border border-warning">
                <Button variant="outline-warning" className="mt-3" onClick={submitForm} >Soumettre</Button>
            </Modal.Footer>
        </Modal>

    );
}
