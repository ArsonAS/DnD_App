import http from "../constants/http";
import {AxiosResponse} from "axios";
import {Client} from "../models/Client";
import {CAMPAIGN_PREFIX, CHARACTER_PREFIX, CLIENT_PREFIX} from "../constants/apiPrefixes";
import {Character} from "../models/Character";
import {Campaign} from "../models/Campaign";
import {JournalEntry} from "../models/JournalEntry";


export const getClientById = async (id: number): Promise<AxiosResponse<Client>> => {
    return http.get<Client>(`${CLIENT_PREFIX}/${id}`);
};
export const updateClientRole = async (clientId: number) => {
    return http.put<Client>(`${CLIENT_PREFIX}/${clientId}`, null,{
        params: {
            clientId
        }
    });
}

export const createCharacter = async (character: Character, clientId: number) => {
    return http.post(CHARACTER_PREFIX, character, {
        params:{
            clientId
        }
    });
}
export const getCharacterById = async (charId: number): Promise<AxiosResponse<Character>> => {
    return http.get<Character>("/api/clients/character", {
        params:{
            charId
        }
    });
};
export const getAllCharactersByClientId = async (clientId: number): Promise<AxiosResponse<Character[]>> => {
    return http.get("/api/clients/characters", {
        params:{
            clientId
        }
    });
};

export const createCampaign = async (campaign: Campaign, clientId: number)=> {
    return http.post(CAMPAIGN_PREFIX, campaign, {
        params:{
            clientId
        }
    });
}
export const getCampaignById = async (campaignId: number): Promise<AxiosResponse<Campaign>> => {
    return http.get<Campaign>("/api/clients/campaign", {
        params:{
            campaignId
        }
    });
};
export const getAllCampaignsByClientId = async (clientId: number): Promise<AxiosResponse<Campaign[]>> => {
    return http.get("/api/clients/campaigns", {
        params:{
            clientId
        }
    });
};
export const getAllActiveCampaigns = async (): Promise<AxiosResponse<Campaign[]>> => {
    return http.get("/api/clients/active_campaigns")
}
export const addCharacterToCampaign = async (charId: number, campaignId: number)=> {
    return http.put<Campaign>("/api/clients/campaign_character", null,{
        params: {
            charId,
            campaignId
        }
    });
}
export const getAllCharactersByCampaignId = async (campaignId: number): Promise<AxiosResponse<Character[]>> => {
    return http.get("/api/clients/campaign_characters", {
        params:{
            campaignId
        }
    });
};
export const getAllCampaignsByCharacterId = async (charId: number): Promise<AxiosResponse<Campaign[]>> => {
    return http.get("/api/clients/character_campaigns", {
        params:{
            charId
        }
    });
};

export const updateFinishedStatus = async (campaignId: number) => {
    return http.put<Campaign>(`/api/clients/campaign`, null,{
        params: {
            campaignId
        }
    });
}
export const updateNotesById = async (campaignId: number, notes: string) => {
    return http.put<Campaign>('/api/clients/notes', null,{
        params: {
            campaignId,
            notes
        }
    });
}

export const createJournalEntry = async (journal: JournalEntry, charId: number)=> {
    return http.post(CAMPAIGN_PREFIX, journal, {
        params:{
            charId
        }
    });
}
export const getJournalEntryById = async (journalId: number): Promise<AxiosResponse<JournalEntry>> => {
    return http.get<JournalEntry>("/api/clients/journal_entry", {
        params:{
            journalId
        }
    });
};
export const getAllJournalEntriesByCharacterId = async (charId: number): Promise<AxiosResponse<JournalEntry[]>> => {
    return http.get("/api/clients/character_campaigns", {
        params:{
            charId
        }
    });
};