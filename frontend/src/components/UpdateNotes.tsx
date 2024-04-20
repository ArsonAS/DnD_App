import {useNavigate} from "react-router-dom";
import React, {useState} from "react";
import {Button, Col, Modal, Row} from "react-bootstrap";
import FormInput from "./FormInput";
import {Campaign} from "../models/Campaign";
import {createCampaign, updateFinishedStatus, updateNotesById} from "../services/clientService";

interface Props {
    show: boolean;
    handleClose: () => void;
    campaignId: number;
    previousNotes: string;
}
export const UpdateNotes = ({show, handleClose, campaignId, previousNotes}: Props) => {
    const navigate = useNavigate();
    const [notes, setNotes] = useState<string>(previousNotes);
    const [errors, setErrors] = useState<string[]>([]);

    const submitForm = () => {
        handleSubmit(campaignId, notes);
    };
    const handleSubmit = (campaignId: number, notes: string) => {
        handleClose();
        updateNotesById(campaignId, notes).then(() => {
            window.location.reload();
        })
    }

    return (
        <Modal show={show} onHide={handleClose} className="p-2  text-warning border border-warning rounded align-content-center">
            <Modal.Header closeButton className="text-warning bg-dark border border-warning">
                <Modal.Title>Notes de la campagne</Modal.Title>
            </Modal.Header>
            <Modal.Body className="bg-dark border border-warning">
                <Row>
                    <Col className='overflow-auto'>
                        <textarea rows={4} cols={60} value={notes} onChange={(e)=> setNotes(e.target.value)}/>

                    </Col>
                </Row>
            </Modal.Body>
            <Modal.Footer className="bg-dark border border-warning">
                <Button variant="outline-warning" className="mt-3" onClick={submitForm} >Soumettre</Button>
            </Modal.Footer>
        </Modal>

    );
}