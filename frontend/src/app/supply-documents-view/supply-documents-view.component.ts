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

  constructor(
    private supplyDocumentService: SupplyDocumentService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadSupplyDocuments();
    console.log(this.supplyDocuments)
  }

  loadSupplyDocuments(): void {
    this.supplyDocumentService.getSupplyDocuments().subscribe(
      (data) => { this.supplyDocuments = data.map(doc => ({ ...doc, selected: false })); },
      (error) => console.error('Error fetching supply documents:', error)
    );
  }

  addSupplyDocument(): void {
    this.router.navigate(['/supplyDocument/add']);
  }
  deleteSelectedDocuments(): void {
    const selectedDocIds = this.supplyDocuments
      .filter(doc => doc.selected)
      .map(doc => doc.id);

    this.supplyDocumentService.deleteSelectedSupplyDocuments(selectedDocIds).subscribe({
      next: () => {
        console.log('Documents deleted successfully');
        this.supplyDocuments = this.supplyDocuments.filter(doc => !selectedDocIds.includes(doc.id));
      },
      error: (error) => {
        console.error('Error deleting documents:', error);
      }
    });
  }


  isAnyDocumentSelected(): boolean {
    return this.supplyDocuments.some(doc => doc.selected);
  }

  checkIfAnySelected(): void {
    this.isAnyDocumentSelected();
  }


}
