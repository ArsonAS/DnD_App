export interface JournalEntry {
    id?: number;
    entry: string;
    entryDate: Date;
    characterId: number;
}
export const formatDate = (date: Date) => {
    const newDate = new Date(date);
    const year = newDate.getFullYear();
    const month = newDate.getMonth() + 1;
    const day = newDate.getDate();

    return `${day}/${month}/${year}`;
};