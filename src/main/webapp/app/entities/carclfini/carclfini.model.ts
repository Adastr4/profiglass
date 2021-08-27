export interface ICARCLFINI {
  id?: number;
  classe?: string;
}

export class CARCLFINI implements ICARCLFINI {
  constructor(public id?: number, public classe?: string) {}
}

export function getCARCLFINIIdentifier(cARCLFINI: ICARCLFINI): number | undefined {
  return cARCLFINI.id;
}
