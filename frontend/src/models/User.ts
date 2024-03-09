export interface User {
    id?: number;
    username?: string;
    email: string;
    password: string;
    currentRole?: string;
}

export enum Role {
    Player,
    DM,
    Admin,
}