import React, {useEffect, useState} from "react";
import {Client} from "../models/Client";
import {Link, useLocation, useNavigate, useParams} from "react-router-dom";
import {Button, ButtonGroup, Col, Container, FormCheck, FormControl, Row} from "react-bootstrap";
import {
    getAllActiveCampaigns,
    getAllCampaignsByClientId,
    getAllCharactersByClientId,
    getClientById,
    updateClientRole
} from "../services/clientService";
import {getClientId, logout} from "../security/authService";
import {NavBar} from "./NavBar";
import {Character} from "../models/Character";
import {Campaign} from "../models/Campaign";
import {CharacterList} from "./CharacterList";
import {CampaignList} from "./CampaignList";
import {AddCampaign} from "./AddCampaign";

export const Dashboard = () => {
    const [show, setShow] = useState<boolean>(false);
    const [client, setClient] = useState<Client>();
    const [characters, setCharacters] = useState<Character[]>([]);
    const [campaigns, setCampaigns] = useState<Campaign[]>([]);
    const navigate = useNavigate();


    useEffect(() => {
        if (client !== undefined) return;
        const id = getClientId()

        getClientById(parseInt(id!)).then((response) => {
            setClient(response.data);
        })

    }, [client]);

    useEffect(() => {
        if (client === undefined) return;
        const clientId = getClientId();
        if (!clientId) return;


        const fetchData = async () => {
            if (client.role === "DM"){
                const campaignsRes = await getAllCampaignsByClientId(parseInt(clientId));
                if(campaignsRes.data) setCampaigns(campaignsRes.data);

            }
            if (client.role === "PLAYER"){
                const charRes = await getAllCharactersByClientId(parseInt(clientId));
                if (charRes.data) setCharacters(charRes.data);


                const activeCampaignsRes = await getAllActiveCampaigns();
                if (activeCampaignsRes.data && charRes.data.length > 0) setCampaigns(activeCampaignsRes.data);

            }
        }
        fetchData();

    }, [client, setCampaigns, setCharacters]);



    const goToCreateCharacter = () => {
        navigate("/character/new");
    }
    const onSwitchAction = () => {
        const id = getClientId()
        updateClientRole(parseInt(id!)).then(() => {
            window.location.reload();
        });
    };

    const handleOpen = () => setShow(true);
    const handleClose = () => setShow(false);

    return (
        <Container fluid className="p-2 bg-dark text-warning min-vh-100">
            <Row className="px-3 sticky-top mb-5 py-2 border border-bottom-2 border-left-0 border-right-0 border-warning align-items-baseline">
                <Col sm={{span: 3, offset: 0}} className="bg-dark px-2 py-1 align-items-baseline">
                    <p>
                        <span className="h3">{client?.username}</span> : <span className="h5">{client?.role}</span>
                    </p>
                </Col>
                <Col sm={{span: 5, offset: 0}} className="bg-dark px-2 py-1">
                    <Row className="px-2 flex-row justify-content-around">
                        {client?.role === "DM"
                            ?
                            <ButtonGroup>
                                <Button variant="warning" onClick={handleOpen}>Créer une campagne</Button>
                            </ButtonGroup>
                            :
                            <ButtonGroup>
                                <Button variant="warning" onClick={goToCreateCharacter}>Créer un personnage</Button>
                            </ButtonGroup>
                        }
                    </Row>
                </Col>
                <Col sm={{span: 3, offset: 0}} className="bg-dark align-self-end">
                    <NavBar/>
                </Col>
            </Row>
            <Row className="px-3 mb-5">
                {client?.role === "DM"
                    ? <Button variant="warning" onClick={onSwitchAction}>Voir la page en tant que joueur</Button>
                    : <Button variant="warning" onClick={onSwitchAction}>Voir la page en tant que MD</Button>
                }
            </Row>
            <Row>
                <Col sm={{span: 6, offset: 0}}>
                    {client?.role === "DM"
                        ? <CampaignList campaigns={campaigns}/>
                        : <CharacterList characters={characters}/>
                    }
                </Col>
                <Col sm={{span: 4, offset: 0}}>
                    {client?.role === "PLAYER" &&  <CampaignList campaigns={campaigns}/>}
                </Col>
            </Row>

            {show && (<AddCampaign show={show} handleClose={handleClose} clientId={client?.id!}/>)}


        </Container>
    );



}