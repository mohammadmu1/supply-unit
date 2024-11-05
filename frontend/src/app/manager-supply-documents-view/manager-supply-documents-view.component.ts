import {Component, OnInit} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgClass, NgForOf} from "@angular/common";
import {SupplyDocument, SupplyDocumentService} from "../service/supply-document.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-manager-supply-documents-view',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf,
    NgClass
  ],
  templateUrl: './manager-supply-documents-view.component.html',
  styleUrl: './manager-supply-documents-view.component.css'
})
export class ManagerSupplyDocumentsViewComponent implements OnInit {

  supplyDocuments: SupplyDocument[] = [];

  constructor(
    private supplyDocumentService: SupplyDocumentService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.loadSupplyDocuments();
  }

  loadSupplyDocuments(): void {
    this.supplyDocumentService.getManagerSupplyDocuments().subscribe(
      (data) => {
        this.supplyDocuments = data.map(doc => ({...doc, selected: false}));
      },
      (error) => console.error('Error fetching supply documents:', error)
    );
  }

  back(): void {
    this.router.navigate(['/warehouse']);
  }


  approveDocument(doc: any) {
    const updatePayload = {
      id: doc.id,
      status: 'Approved'
    };

    this.supplyDocumentService.updateDocumentStatus(updatePayload).subscribe({
      next: () => {
        console.log('Document approved successfully.');
        // Remove the document from the array after approval
        this.supplyDocuments = this.supplyDocuments.filter(d => d.id !== doc.id);
      },
      error: (error) => {
        console.error('Error approving document:', error);
      }
    });
  }

  declineDocument(doc: any) {
    const updatePayload = {
      id: doc.id,
      status: 'Decline'
    };

    this.supplyDocumentService.updateDocumentStatus(updatePayload).subscribe({
      next: () => {
        console.log('Document declined successfully.');
        // Remove the document from the array after decline
        this.supplyDocuments = this.supplyDocuments.filter(d => d.id !== doc.id);
      },
      error: (error) => {
        console.error('Error declining document:', error);
      }
    });
  }

}
