export interface ICLLEGA {
  id?: number;
  opzione?: string;
  descrizione?: string | null;
}

export class CLLEGA implements ICLLEGA {
  constructor(public id?: number, public opzione?: string, public descrizione?: string | null) {}
}

export function getCLLEGAIdentifier(cLLEGA: ICLLEGA): number | undefined {
  return cLLEGA.id;
}
