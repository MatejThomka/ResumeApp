export interface Education {

  id?: number;
  educationType: string | null;
  nameOfInstitution: string;
  faculty: string;
  name: string;
  fieldOfStudy: string;
  city: string;
  schoolLeavingExam: boolean;
  yearFrom: number | null;
  isStudying: boolean;
  yearTill: number | null;
  description: string;
}
