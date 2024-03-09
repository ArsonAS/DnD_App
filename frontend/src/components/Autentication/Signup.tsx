import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import {Button, Col, Container, Row, Alert} from "react-bootstrap";
import Form from "react-bootstrap/Form";
import {User} from "../../models/User";
import {validateEmail, validatePassword, validatePasswordConfirmation} from "../../services/validationService";
import FormInput from "../FormInput";
import {signup} from "../../services/signupService";


const errorStringValue = "signup.errors.network";

export const Signup = () => {
    const navigate = useNavigate();
    const [username, setUsername] = useState<string>("");
    const [currentRole, setCurrentRole] = useState<string>("")
    const [email, setEmail] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [passwordConfirmation, setPasswordConfirmation] = useState<string>("");
    const [isDisabled, setIsDisabled] = useState<boolean>(false);
    const [unexpectedError, setUnexpectedError] = useState<string>("");
    const [errors, setErrors] = useState<string[]>([]);


    const submitForm = () => {
        if (!validateForm()) return;

        let user: User = {
            username,
            email,
            password,
            currentRole
        };


        sendRequest(user);
    };

    const handleSubmit = (user: User) => {


        return signup(user);
    };

    const sendRequest = (user: User) => {

        handleSubmit(user)
            .then((_) => {
                setUnexpectedError("");
                navigate("/login/createdUser");
            })
            .catch((err) => {
                if (err.code === "ERR_NETWORK") {
                    setUnexpectedError(errorStringValue);
                } else {
                    setUnexpectedError(err.status);
                }
            });
    };

    const validateForm = (): boolean => {
        let isFormValid = true;
        let errorsToDisplay: string[] = [];

        if (!validateUsername(errorsToDisplay)) isFormValid = false;


        if (!validateEmail(email)) {
            errorsToDisplay.push("Veuillez entrer une adresse courriel valide");
            isFormValid = false;
        }
        if (!validatePassword(password)) {
            errorsToDisplay.push("Veuillez entrer un mot de passe valide");
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

            <div className="p-2 m-5 bg-dark text-warning border border-warning rounded vh-50 vw-50">
                <Row>
                    <FormInput label="Nom d'usager"
                               value={username}
                               onChange={(e)=> setUsername(e.target.value)}
                               errors={errors}
                               formError="Le Nom d'usager ne peut pas être vide"
                               controlId="userName"
                    />
                </Row>
                <Row>
                    <FormInput label="Adresse courriel"
                               value={email}
                               onChange={(e)=> setEmail(e.target.value)}
                               errors={errors}
                               formError="Veuillez entrer une adresse courriel valide"
                               controlId="email"
                               type="email"
                    />
                </Row>
                <Row>
                    <FormInput label="Choisissez un mot de passe"
                               value={password}
                               onChange={(e)=> setPassword(e.target.value)}
                               errors={errors}
                               formError="Veuillez entrer un mot de passe valide"
                               controlId="userPassword"
                               type="password"
                    />
                </Row>
                <Row>
                    <FormInput label="Confirmez votre mot de passe"
                               value={passwordConfirmation}
                               onChange={(e)=> setPasswordConfirmation(e.target.value)}
                               errors={errors}
                               formError="Les mots de passe ne correspondent pas"
                               controlId="passwordconfirmation"
                               type="password"
                    />
                </Row>

                <Button variant="outline-warning" className="mt-3" onClick={submitForm} disabled={isDisabled}>S'inscrire</Button>
                {unexpectedError !== "" && (
                    <Alert variant="danger" className="mt-2">
                        {unexpectedError === errorStringValue ? errorStringValue : unexpectedError}
                    </Alert>
                )}
            </div>


        </Container>


    );
}
