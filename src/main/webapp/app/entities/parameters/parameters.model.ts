export interface IParameters {
  id?: number;
  key?: string;
  value?: string;
  description?: string | null;
}

export class Parameters implements IParameters {
  constructor(public id?: number, public key?: string, public value?: string, public description?: string | null) {}
}

export function getParametersIdentifier(parameters: IParameters): number | undefined {
  return parameters.id;
}
