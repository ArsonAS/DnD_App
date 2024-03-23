import {Button, ButtonGroup, Col, Container, Form, FormControl, Row} from "react-bootstrap";
import {useState} from "react";
import {Character} from "../models/Character";
import {useLocation, useNavigate, useParams} from "react-router-dom";

export const CharacterPage = () => {
    const [character, setCharacter] = useState<Character>();
    const navigate = useNavigate();
    const location = useLocation();
    const params = useParams();

    return (
        <Container fluid className="p-2 bg-dark vh-100 text-warning">


        </Container>
    );
}