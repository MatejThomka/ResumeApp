export interface Education {

  id?: number;
  name: string;
  educationType: string;
  fieldOfStudy: string;
  yearFrom: number;
  yearTill?: number;
  description: string;
  studying: boolean;
  city: string;
  schoolLeavingExam: boolean;
  faculty: string;
  nameOfInstitution: string;
}
