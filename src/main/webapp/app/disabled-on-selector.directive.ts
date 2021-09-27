import { Directive, Input, OnChanges, ElementRef, Renderer2 } from '@angular/core';

@Directive({
  selector: '[jhiNgxDisabledOnSelector]',
})
export class DisabledOnSelectorDirective implements OnChanges {
  @Input()
  jhiNgxDisabledOnSelector!: string;
  @Input()
  disabled!: boolean;
  private readonly nativeElement: HTMLElement;

  constructor(private el: ElementRef, private renderer2: Renderer2) {
    this.nativeElement = el.nativeElement;
  }

  ngOnChanges(): void {
    const elements = this.nativeElement.querySelectorAll(this.jhiNgxDisabledOnSelector);
    elements.forEach(element => {
      this.renderer2.setProperty(element, 'disabled', this.disabled);
    });
  }
}
