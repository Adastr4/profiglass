import { TreeviewItem } from 'ngx-treeview';

export class TreeViewService {
  getBooks(): TreeviewItem[] {
    const itCategory = new TreeviewItem({
      text: 'Configuratore',
      value: 9,
      children: [
        {
          text: 'Caratteristica',
          value: 911,
          children: [
            { text: 'Lega', value: 9111 },
            { text: 'Stato Fisico', value: 9112 },
            { text: 'Finitura', value: 9113, disabled: true },
          ],
        },
        {
          text: 'Ciclo',
          value: 911,
          children: [
            { text: 'Angular 1', value: 9111 },
            { text: 'Angular 2', value: 9112 },
            { text: 'ReactJS', value: 9113, disabled: true },
          ],
        },
        {
          text: 'Distinta',
          value: 912,
          children: [
            { text: 'C#', value: 9121 },
            { text: 'Java', value: 9122 },
            { text: 'Python', value: 9123, checked: false, disabled: true },
          ],
        },
      ],
    });

    return [itCategory];
  }
}
