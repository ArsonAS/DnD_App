
import {Alert, Button, Col, Container, FormControl, Row} from "react-bootstrap";
import FormInput from "../FormInput";
import {Link, useLocation, useNavigate} from "react-router-dom";
import React, {useState} from "react";
import {LoginRequest} from "../../security/authentication";
import {authenticate, getClientId, login, logout} from "../../security/authService";
import {getClientById} from "../../services/clientService";


export const Login = () =>{
    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [errors, setErrors] = useState<string[]>([]);
    const [areCredentialsValid, setAreCredentialsValid] = useState<boolean>(true);

    const navigate = useNavigate();
    const location = useLocation();

    const submitForm = () => {
        if (!validateForm()) return;


        let loginRequest: LoginRequest = {
            username: username,
            password: password,
        };

        login(loginRequest)
            .then((response) => {
                authenticate(response.data);

                const id = getClientId();

                if (id == null) {
                    logout();
                    navigate("/pageNotFound");
                    return;
                }

                getClientById(parseInt(id))
                    .then((response) => {
                        navigate("/clientpage/" + id);
                    })
                    .catch((err) => {
                        logout();
                        navigate("/pageNotFound");
                    });
            })
            .catch((error) => {
                if (error.response.status === 401 || error.response.status === 403)
                    setAreCredentialsValid(false);
            });
    };

    const validateForm = (): boolean => {
        let isFormValid = true;
        let errorsToDisplay: string[] = [];

        if (!validateUsername(errorsToDisplay)) isFormValid = false;
        if (!validatePassword(errorsToDisplay)) isFormValid = false;

        setErrors(errorsToDisplay);

        return isFormValid;
    };
    const validateUsername = (errorsToDisplay: string[]): boolean => {
        if (username === "") {
            errorsToDisplay.push("login.errors.emptyIdentification");
            return false;
        }
        return true;
    };

    const validatePassword = (errorsToDisplay: string[]): boolean => {
        if (password === "") {
            errorsToDisplay.push("login.errors.emptyPassword");
            return false;
        }
        return true;
    };

    return (
        <Container fluid className="p-2 bg-dark vh-100">
            <div className="p-2 m-5 bg-dark text-warning border border-warning rounded vh-50 vw-50 align-content-center">
                <Row>
                    <FormInput label="Nom d'usager" value={username} onChange={(e)=> setUsername(e.target.value)}
                               errors={errors} formError="Veuillez entrer votre nom d'usager"
                               controlId="userame"
                    />
                </Row>

                <Row>
                    <FormInput label="Mot de passe" value={password} onChange={(e)=> setPassword(e.target.value)}
                               errors={errors} formError="Veuillez entrer votre mot de passe"
                               controlId="password" type="password"
                    />
                </Row>

                <Button variant="outline-warning" className="mt-3" onClick={submitForm} >Se connecter</Button>

                <Link color="outline-warning" to={"/signup"}>Vous n'avez pas de compte ? s'inscrire</Link>
                {!areCredentialsValid && (
                    <Alert variant="danger mt-3">Le nom d'ussager ou le mot de passe est invalide"</Alert>
                )}
            </div>
        </Container>
    );
}