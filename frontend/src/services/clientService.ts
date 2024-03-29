import http from "../constants/http";
import {AxiosResponse} from "axios";
import {Client} from "../models/Client";
import {CHARACTER_PREFIX, CLIENT_PREFIX} from "../constants/apiPrefixes";
import {Character} from "../models/Character";


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