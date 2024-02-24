import { Component } from "react";
import { Button } from "react-bootstrap";
import Form from "react-bootstrap/Form";

interface Props {}

export const Signup = () =>{
    return (
        <Form>
            <Form.Group controlId="userEmail">
                <Form.Label>Adresse courriel</Form.Label>
                <Form.Control type="email" placeholder="Saissir votre courriel"/>
            </Form.Group>

            <Form.Group controlId="userName">
                <Form.Label>Nom d'usager</Form.Label>
                <Form.Control type="text" placeholder="Saissir votre nom d'usager"/>
            </Form.Group>

            <Form.Group controlId="userPassword">
                <Form.Label>Mot de passe</Form.Label>
                <Form.Control type="password" placeholder="Saissir votre nom mot de passe"/>
            </Form.Group>

            <Form.Group controlId="passwordconfirmation">
                <Form.Label>Mot de passe</Form.Label>
                <Form.Control type="password" placeholder="Confirmer votre nom mot de passe"/>
            </Form.Group>

            <Button variant="primary" type="submit">
                Submit
            </Button>

        </Form>

    );
}
