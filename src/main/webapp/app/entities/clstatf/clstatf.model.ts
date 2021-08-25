export interface ICLSTATF {
  id?: number;
  opzione?: string;
  descrizione?: string | null;
}

export class CLSTATF implements ICLSTATF {
  constructor(public id?: number, public opzione?: string, public descrizione?: string | null) {}
}

export function getCLSTATFIdentifier(cLSTATF: ICLSTATF): number | undefined {
  return cLSTATF.id;
}
