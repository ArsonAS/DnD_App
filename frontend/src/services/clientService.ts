import http from "../constants/http";
import {AxiosResponse} from "axios";
import {Client} from "../models/Client";
import {CHARACTER_PREFIX, CLIENT_PREFIX} from "../constants/apiPrefixes";
import {Character} from "../models/Character";


export const getClientById = async (id: number): Promise<AxiosResponse<Client>> => {
    return http.get<Client>(`${CLIENT_PREFIX}/${id}`);
};


export const createCharacter = async (character: Character) => {
    return http.post(CHARACTER_PREFIX, character);
}
export const getCharacterById = async (charId: number): Promise<AxiosResponse<Character>> => {
    return http.get<Character>(CHARACTER_PREFIX, {
        params:{
            charId,
        }
    });
};