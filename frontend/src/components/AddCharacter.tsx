import React, {useState} from "react";
import {Character} from "../models/Character";
import {Button, Col, Container, Form, FormControl, Row} from "react-bootstrap";
import FormInput from "./FormInput";
import {useNavigate} from "react-router-dom";
import {getClientId} from "../security/authService";
import {createCharacter} from "../services/clientService";

export const AddCharacter = () => {
    const navigate = useNavigate();

    const [name, setName] = useState<string>("");
    const [classe, setClasse] = useState<string>("");
    const [level, setLevel] = useState<number>(0);
    const [background, setBackground] = useState<string>("");
    const [race, setRace] = useState<string>("");
    const [alignment, setAlignment] = useState<string>("");
    const [experiencePoints, setExperiencePoints] = useState<number>(0);

    const [strength, setStrength] = useState<number>(10);
    const [dexterity, setDexterity] = useState<number>(10);
    const [constitution, setConstitution] = useState<number>(10);
    const [intelligence, setIntelligence] = useState<number>(10);
    const [wisdom, setWisdom] = useState<number>(10);
    const [charisma, setCharisma] = useState<number>(10);
    const [clientId, setClientId] = useState<string>("");

    const [errors, setErrors] = useState<string[]>([]);

    const submitForm = () => {
        if (!validateForm()) return;
        const id = getClientId();
        if (id !== null){
            setClientId(id!);
        }


        let character: Character = {
            name,
            classe,
            level,
            experiencePoints,
            background,
            race,
            alignment,
            characterAbilityScores: {
                strength,
                dexterity,
                constitution,
                intelligence,
                wisdom,
                charisma,
            },
            clientId,
        };
        handleSubmit(character);
    };

    const handleSubmit = (character: Character) => {
        createCharacter(character).then(() => {
            navigate("/clientpage/" + getClientId());
        })
    }


    const validateForm = (): boolean => {
        let isFormValid = true;
        let errorsToDisplay: string[] = [];

        if (!validateName(errorsToDisplay)) isFormValid = false;

        setErrors(errorsToDisplay);

        return isFormValid;
    };

    const validateName = (errorsToDisplay: string[]): boolean => {
        if (name === "") {
            errorsToDisplay.push("Le Nom d'usager ne peut pas être vide");
            return false;
        }

        return true;
    };


    return (
        <Container fluid className="p-2 bg-dark vh-100">
            <div className="p-2 m-5 bg-dark text-warning border border-warning rounded vh-50 vw-50 align-content-center">
                <Row>
                    <Col>
                        <FormInput label="Nom du charactere" value={name} onChange={(e)=> setName(e.target.value)}
                                   errors={errors} formError="Le Nom du charactere ne peut pas être vide"
                                   controlId="name"
                        />
                    </Col>
                    <Col>
                        <Form.Group controlId="classe">
                            <Form.Label sm={3}>Choisissez une classe</Form.Label>
                            <FormControl as="select" onChange={(e) => setClasse(e.target.value)}>
                                <option value={"Barbare"}>Barbare</option>
                                <option value={"Barde"}>Barde</option>
                                <option value={"Clerc"}>Clerc</option>
                                <option value={"Druide"}>Druide</option>
                                <option value={"Combattante"}>Combattante</option>
                                <option value={"Moine"}>Moine</option>
                                <option value={"Paladin"}>Paladin</option>
                                <option value={"Ranger"}>Ranger</option>
                                <option value={"Voleur"}>Voleur</option>
                                <option value={"Sorcier"}>Sorcier</option>
                                <option value={"Magicien"}>Magicien</option>
                            </FormControl>
                        </Form.Group>
                    </Col>
                </Row>

                <Row>
                    <Col>
                        <FormInput label="Niveau" value={level.toString()} onChange={(e)=> setLevel(parseInt(e.target.value))}
                                   errors={errors} formError=""
                                   controlId="level"
                        />
                    </Col>
                    <Col>
                        <FormInput label="Points d'experience" value={experiencePoints.toString()} onChange={(e)=> setExperiencePoints(parseInt(e.target.value))}
                                   errors={errors} formError=" "
                                   controlId="experiencePoints"
                        />
                    </Col>
                </Row>

                <Row>
                    <Col >
                        <Form.Group controlId="background">
                            <Form.Label sm={3}>Choisissez un origine</Form.Label>
                            <FormControl as="select" onChange={(e) => setBackground(e.target.value)}>
                                <option value={"Acolyte"}>Acolyte</option>
                                <option value={"Charlatan"}>Charlatan</option>
                                <option value={"Criminel"}>Criminel</option>
                                <option value={"Artiste"}>Artiste</option>
                                <option value={"Héros populaire"}>Héros populaire</option>
                                <option value={"Artisan de guilde"}>Artisan de guilde</option>
                                <option value={"Noble"}>Noble</option>
                                <option value={"Sage"}>Sage</option>
                                <option value={"Soldat"}>Soldat</option>
                                <option value={"Oursin"}>Oursin</option>
                            </FormControl>
                        </Form.Group>
                    </Col>
                    <Col >
                        <Form.Group controlId="race">
                            <Form.Label sm={3}>Choisissez une race</Form.Label>
                            <FormControl as="select" onChange={(e) => setRace(e.target.value)}>
                                <option value={"Humaine"}>Humaine</option>
                                <option value={"Elfe"}>Elfe</option>
                                <option value={"Halfelin"}>Halfelin</option>
                                <option value={"Nain"}>Nain</option>
                                <option value={"Tieffelin"}>Tieffelin</option>
                            </FormControl>
                        </Form.Group>
                    </Col>

                    <Col>
                        <Form.Group controlId="alignment">
                            <Form.Label sm={3}>Choisissez un alignment</Form.Label>
                            <FormControl as="select" onChange={(e) => setAlignment(e.target.value)}>
                                <option value={"Bien légitime"}>Bien légitime</option>
                                <option value={"Bien neutre"}>Neutre bien</option>
                                <option value={"Bien chaotique"}>Bien chaotique</option>
                                <option value={"Neutre légitime"}>Neutre légitime</option>
                                <option value={"Neutre"}>Neutre</option>
                                <option value={"Neutre chaotique"}>Neutre chaotique</option>
                                <option value={"Mal légitime"}>Mal légitime</option>
                                <option value={"Mal neutre"}>Mal neutre</option>
                                <option value={"Mal chaotique"}>Mal chaotique</option>
                            </FormControl>
                        </Form.Group>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <FormInput label="Force" value={strength.toString()} onChange={(e)=> setStrength(parseInt(e.target.value))}
                                   errors={errors} formError=" "
                                   controlId="strength"
                        />
                    </Col>
                    <Col>
                        <FormInput label="Dextérité" value={dexterity.toString()} onChange={(e)=> setDexterity(parseInt(e.target.value))}
                                   errors={errors} formError=" "
                                   controlId="dexterity"
                        />
                    </Col>
                </Row>

                <Row>
                    <Col>
                        <FormInput label="Constitution" value={constitution.toString()} onChange={(e)=> setConstitution(parseInt(e.target.value))}
                                   errors={errors} formError=" "
                                   controlId="constitution"
                        />
                    </Col>
                    <Col>
                        <FormInput label="Intelligence" value={intelligence.toString()} onChange={(e)=> setIntelligence(parseInt(e.target.value))}
                                   errors={errors} formError=" "
                                   controlId="intelligence"
                        />
                    </Col>

                </Row>

                <Row>
                    <Col>
                        <FormInput label="Sagesse" value={wisdom.toString()} onChange={(e)=> setWisdom(parseInt(e.target.value))}
                                   errors={errors} formError=" "
                                   controlId="wisdom"
                        />
                    </Col>
                    <Col>
                        <FormInput label="Charisme" value={charisma.toString()} onChange={(e)=> setCharisma(parseInt(e.target.value))}
                                   errors={errors} formError=" "
                                   controlId="charisma"
                        />
                    </Col>
                </Row>
                <Button variant="outline-warning" className="mt-3" onClick={submitForm} >Soumettre</Button>

            </div>



        </Container>
    );
}