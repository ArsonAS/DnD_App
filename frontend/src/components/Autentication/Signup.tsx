import React, { useState } from "react";
import {Link, useNavigate} from "react-router-dom";
import {Button, Col, Container, Form, FormControl, FormSelect, Row} from "react-bootstrap";
import {Client} from "../../models/Client";
import {validateEmail, validatePassword, validatePasswordConfirmation} from "../../services/validationService";
import FormInput from "../FormInput";

import {authenticate, getClientId, login, logout, signup} from "../../security/authService";
import {LoginRequest} from "../../security/authentication";


export const Signup = () => {
    const navigate = useNavigate();
    const [username, setUsername] = useState<string>("");
    const [role, setRole] = useState<string>("PLAYER")
    const [email, setEmail] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [passwordConfirmation, setPasswordConfirmation] = useState<string>("");
    const [errors, setErrors] = useState<string[]>([]);

    const submitForm = () => {
        if (!validateForm()) return;
        let client: Client = {
            username,
            email,
            password,
            role: role
        };
        handleSubmit(client);
    };

    const handleSubmit = (user: Client) => {
        signup(user).then(()=>{
            let loginRequest: LoginRequest = {
                username: username,
                password: password,
            };
            login(loginRequest).then(response => {
                authenticate(response.data);
                const id = getClientId();

                navigate("/clientpage/" + id);
            })
        });
    }


    const validateForm = (): boolean => {
        let isFormValid = true;
        let errorsToDisplay: string[] = [];

        if (!validateUsername(errorsToDisplay)) isFormValid = false;

        if (!validateEmail(email)) {
            errorsToDisplay.push("Veuillez entrer une adresse courriel valide");
            isFormValid = false;
        }
        if (!validatePassword(password)) {
            errorsToDisplay.push("Doit avoir minimum 8 caracteres, une lettre majuscule, une lettre minuscule et une chiffre");
            isFormValid = false;
        }
        if (!validatePasswordConfirmation(password, passwordConfirmation)) {
            errorsToDisplay.push("Les mots de passe ne correspondent pas");
            isFormValid = false;
        }

        setErrors(errorsToDisplay);

        return isFormValid;
    };

    const validateUsername = (errorsToDisplay: string[]): boolean => {
        if (username === "") {
            errorsToDisplay.push("Le Nom d'usager ne peut pas être vide");
            return false;
        }

        return true;
    };

    return (
        <Container fluid className="p-2 bg-dark vh-100">
            <div className="p-2 m-5 bg-dark text-warning border border-warning rounded vh-50 vw-50 align-content-center">
                <Row>
                    <FormInput label="Nom d'usager" value={username} onChange={(e)=> setUsername(e.target.value)}
                               errors={errors} formError="Le Nom d'usager ne peut pas être vide"
                               controlId="userName"
                    />
                </Row>

                <Row>
                    <Col sm={12} md={true}>
                        <Form.Group controlId="userRole">
                            <Form.Label sm={3}>Role</Form.Label>
                            <FormControl as="select" onChange={(e) => setRole(e.target.value)}>
                                <option value={"PLAYER"}>Joueur</option>
                                <option value={"DM"}>Maître du donjon</option>
                            </FormControl>
                        </Form.Group>
                    </Col>
                </Row>

                <Row>
                    <FormInput label="Adresse courriel" value={email} onChange={(e)=> setEmail(e.target.value)}
                               errors={errors} formError="Veuillez entrer une adresse courriel valide"
                               controlId="email" type="email"
                    />
                </Row>
                <Row>
                    <FormInput label="Choisissez un mot de passe" value={password} onChange={(e)=> setPassword(e.target.value)}
                               errors={errors} formError="Doit avoir minimum 8 caracteres, une lettre majuscule, une lettre minuscule et une chiffre"
                               controlId="userPassword" type="password"
                    />
                </Row>
                <Row>
                    <FormInput label="Confirmez votre mot de passe" value={passwordConfirmation} onChange={(e)=> setPasswordConfirmation(e.target.value)}
                               errors={errors} formError="Les mots de passe ne correspondent pas"
                               controlId="passwordconfirmation" type="password"
                    />
                </Row>
                <Row className="align-items-center">
                    <Col sm={3}>
                        <Button variant="outline-warning" className="mt-3" onClick={submitForm} >S'inscrire</Button>
                    </Col>
                    <Col>
                        <Link color="outline-warning" to={"/"}>Avez-vous un compte? Se connecter</Link>
                    </Col>
                </Row>



            </div>
        </Container>
    );
}
