export interface Shift {
    id: number;
    begin: string;
    end: string;
    worktime:string;
    dailyOvertimed?:boolean;
}

export interface ShiftSucess {
    success: boolean;
}
