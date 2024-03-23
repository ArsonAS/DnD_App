export interface Character{
    id?: number;
    name: string;
    classe : string;
    level: number;
    background: string;
    alignment: string;
    experiencePoints: string;
    characterAbilityScores: {
        strength: number;
        dexterity: number;
        constitution: number;
        intelligence: number;
        wisdom: number;
        charisma: number;
    };
    characterSkills: {
        acrobatics: number;
        animalHandling: number;
        arcana: number;
        athletics: number;
        deception: number;
        history: number;
        insight: number;
        intimidation: number;
        investigation: number;
        medicine: number;
        nature: number;
        perception: number;
        performance: number;
        persuasion: number;
        religion: number;
        sleightofHand: number;
        stealth: number;
        survival: number;
    }

}