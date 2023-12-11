import { AbstractControl, ValidationErrors } from "@angular/forms";

export function dateRangeValidator(control: AbstractControl): ValidationErrors | null {
    const startDate = control.get('startDate')?.value;
    const endDate = control.get('endDate')?.value;
  
    if (startDate && endDate && startDate > endDate) {
      return { dateRange: true };
    }
  
    return null;
}
  
export function imageValidator(control: AbstractControl): ValidationErrors | null {
    const fileName = control.value;
    
    if (fileName) {
        const allowedExts = ["jpeg", "jpg", "png", "gif"];
        const fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

        if (!allowedExts.includes(fileExtension)) {
            return { invalidImageFormat: true };
        }
    }

    return null;
}