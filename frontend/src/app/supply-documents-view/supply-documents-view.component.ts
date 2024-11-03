import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { SupplyDocumentService, SupplyDocument } from '../service/supply-document.service';
import { FormsModule } from "@angular/forms";

@Component({
  selector: 'app-supply-documents-view',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './supply-documents-view.component.html',
  styleUrls: ['./supply-documents-view.component.css']
})
export class SupplyDocumentsViewComponent implements OnInit {

  supplyDocuments: SupplyDocument[] = [];
  selectAll = false;

  constructor(
    private supplyDocumentService: SupplyDocumentService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadSupplyDocuments();
  }

  loadSupplyDocuments(): void {
    this.supplyDocumentService.getSupplyDocuments().subscribe(
      (data) => { this.supplyDocuments = data.map(doc => ({ ...doc, selected: false })); },
      (error) => console.error('Error fetching supply documents:', error)
    );
  }

  addSupplyDocument(): void {
    this.router.navigate(['/supply-documents/add']);
  }



  viewDocumentDetails(documentId: number): void {
    this.router.navigate(['/supply-documents', documentId, 'details']);
  }

  isAnyDocumentSelected(): boolean {
    return this.supplyDocuments.some(doc => doc.selected);
  }

  checkIfAnySelected(): void {
    this.isAnyDocumentSelected();
  }


}
