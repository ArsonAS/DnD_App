import {useNavigate} from "react-router-dom";
import {Button, Col, Form, Modal, Row} from "react-bootstrap";
import FormInput from "./FormInput";
import React, {useState} from "react";
import {formatDate, JournalEntry} from "../models/JournalEntry";
import {createCampaign, createJournalEntry} from "../services/clientService";

interface Props {
    show: boolean;
    handleClose: () => void;
    characterId: number;
}
export const AddJournalEntry = ({show, handleClose, characterId}: Props) => {
    const navigate = useNavigate();
    const [entry, setEntry] = useState<string>("");
    const [entryDate, setEntryDate] = useState<Date>(new Date());
    const [errors, setErrors] = useState<string[]>([]);

    const formatDateForInput = (date: Date | null): string => {
        if (date instanceof Date) {
            return date.toISOString().substring(0, 10);
        }
        return "";
    };
    function isValidDate(d: any) {
        return d instanceof Date && !isNaN(d.getTime());
    }

    const handleDateChange = (setter: React.Dispatch<React.SetStateAction<Date>>, value: string) => {
        const newDate = new Date(value);
        if (isValidDate(newDate)) {
            setter(newDate);
        }
    };

    const validateForm = (): boolean => {
        let isFormValid = true;
        let errorsToDisplay: string[] = [];

        if (entry === "") {
            errorsToDisplay.push("Le journal ne peut pas être vide");
            isFormValid = false;
        }
        if (!isValidDate(entryDate)){
            isFormValid = false;
        }
        setErrors(errorsToDisplay);
        return isFormValid;
    };


    const submitForm = () => {
        if (!validateForm()) return;
        let journalEntry: JournalEntry = {
            entry,
            entryDate,
            characterId,
        };
        handleSubmit(journalEntry, characterId);
    };
    const handleSubmit = (journalEntry: JournalEntry, characterId: number) => {
        handleClose();
        createJournalEntry(journalEntry, characterId).then(() => {

            window.location.reload();
        })
    }
    return (
        <Modal show={show} onHide={handleClose} className="p-2  text-warning border border-warning rounded align-content-center">
            <Modal.Header closeButton className="text-warning bg-dark border border-warning">
                <Modal.Title>Nouveau log</Modal.Title>
            </Modal.Header>
            <Modal.Body className="bg-dark border border-warning">
                <Row>
                    <Col>
                        <Form.Group controlId="formDate">
                            <Form.Label>Date</Form.Label>
                            <Form.Control type="date" value={formatDateForInput(entryDate)}
                                onChange={(e) => handleDateChange(setEntryDate, e.target.value)}
                            />

                        </Form.Group>
                        <FormInput label="" value={entry} onChange={(e)=> setEntry(e.target.value)}
                                   errors={errors} formError="Le journal ne peut pas être vide"
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