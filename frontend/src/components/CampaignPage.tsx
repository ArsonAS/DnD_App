import {Button, ButtonGroup, Col, Container, Row} from "react-bootstrap";
import {NavBar} from "./NavBar";
import React, {useEffect, useState} from "react";
import {Character} from "../models/Character";
import { useParams} from "react-router-dom";
import {
    getAllActiveCampaigns,
    getAllCampaignsByClientId,
    getAllCharactersByCampaignId, getAllCharactersByClientId,
    getCampaignById,
    getCharacterById,
    getClientById, updateFinishedStatus
} from "../services/clientService";
import {Campaign} from "../models/Campaign";
import {CharacterList} from "./CharacterList";
import {Client} from "../models/Client";
import {getClientId} from "../security/authService";
import {AddCampaign} from "./AddCampaign";
import {JoinCampaign} from "./JoinCampaign";
import {UpdateNotes} from "./UpdateNotes";

export const CampaignPage = () => {
    const [client, setClient] = useState<Client>();
    const [campaign, setCampaign] = useState<Campaign>();
    const [characters, setCharacters] = useState<Character[]>([]);
    const [clientCharacters, setClientCharacters] = useState<Character[]>([])
    const [filteredCharacters, setFilteredCharacters] = useState<Character[]>([]);
    const [showJoinCampaign, setShowJoinCampaign] = useState<boolean>(false);
    const [showUpdateNotes, setShowUpdateNotes] = useState<boolean>(false);
    const params = useParams();
    const [disabled, setDisables] = useState<boolean>(true)


    useEffect(() => {
        if (campaign !== undefined) return;
        const id = parseInt(params.id!);

        getCampaignById(id).then((response) => {
            setCampaign(response.data);
        })

    },[campaign]);

    useEffect(() => {
        if (campaign === undefined) return;
        const clientId = getClientId();
        if (!clientId) return;
        const fetchData = async () => {
            const charCampResp = await getAllCharactersByCampaignId(campaign.id!);
            if (charCampResp.data) setCharacters(charCampResp.data);

            const clientRes = await getClientById(parseInt(clientId));
            if (clientRes.data) setClient(clientRes.data);

        }
        fetchData();

    }, [campaign, setCharacters, setClient]);

    useEffect(() => {
        if (campaign === undefined) return;
        const clientId = getClientId();
        if (!clientId) return;
        const fetchData2 = async () => {

            const clientCharRes = await getAllCharactersByClientId(parseInt(clientId));
            if (clientCharRes.data) setClientCharacters(clientCharRes.data);

            const filteredChars: Character[] = await filterCharacterList(clientCharacters, characters);
            if (filteredChars) setFilteredCharacters(filteredChars);
        }
        fetchData2();
    }, [campaign, client, setClientCharacters, setFilteredCharacters]);




    const filterCharacterList = async (arr1: Character[], arr2: Character[]) => {
        let filteredChars: Character[] = [];
        arr1.forEach((clientChar) => {
            let found = false;
            arr2.forEach(char => {
                if (clientChar.id === char.id) found = true;
            })
            if (!found) filteredChars.push(clientChar)
        });
        setFilteredCharacters(filteredChars);
        return filteredChars;
    }
    const handleOpenJoinCampaign = () => setShowJoinCampaign(true);
    const handleCloseJoinCampaign = () => setShowJoinCampaign(false);

    const handleOpenUpdateNotes = () => setShowUpdateNotes(true);
    const handleCloseUpdateNotes = () => setShowUpdateNotes(false);

    const addCharacter = () => {
        const clientId = getClientId();
        if (!clientId) return;
        handleOpenJoinCampaign();
    }
    const finishCampaign = () => {
        if (campaign === undefined) return;
        updateFinishedStatus(campaign.id!).then(() => {
            window.location.reload();
        });
    }

    return (
        <>
            <Container fluid className="p-3 bg-dark vh-100 text-warning border border-warning">
                <Row className="px-3 fixed-top">
                    <Col sm={{span: 12, offset: 0}} className="bg-dark bg-gradient border border-warning ">
                        <NavBar/>
                    </Col>
                    <Col sm={{span: 12, offset: 0}}>
                        <Row>
                            <Col sm={{span: 4, offset: 0}} className="bg-dark bg-gradient border border-warning px-2 py-1">
                                <h4>{campaign?.name}</h4>
                            </Col>
                            <Col className="bg-dark border border-warning px-2 py-2">
                                <Row className="px-2 flex-row justify-content-around">
                                    {campaign?.finished === false && (
                                        client?.role === "DM" ?
                                            <ButtonGroup>
                                                <Button variant="warning" onClick={handleOpenUpdateNotes}>Ecrire Note</Button>
                                                <Button variant="warning" onClick={finishCampaign}>Finir Campagne</Button>
                                            </ButtonGroup>
                                            :<Button variant="warning" onClick={addCharacter} >Ajouter un personnage</Button>
                                    )}
                                </Row>
                            </Col>
                        </Row>
                    </Col>
                    <Col sm={{span: 4, offset: 0}} className="border border-warning vh-100 overflow-auto">
                        <CharacterList characters={characters}/>
                    </Col>
                    <Col sm={{span: 7, offset: 0}} className=" vh-100 overflow-auto">
                        {campaign && campaign.notes}
                    </Col>
                </Row>

                {showJoinCampaign && (<JoinCampaign show={showJoinCampaign} handleClose={handleCloseJoinCampaign} characters={filteredCharacters} campaignId={campaign?.id!}/>)}
                {showUpdateNotes && (<UpdateNotes show={showUpdateNotes} handleClose={handleCloseUpdateNotes} campaignId={campaign?.id!} previousNotes={campaign?.notes!}/>)}


            </Container>
        </>

    );
}