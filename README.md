# Gestion des Notes et Modules

## Gestion des entités
L'application permet de gérer plusieurs entités essentielles pour le bon fonctionnement du système de gestion des notes, telles que les professeurs, filières, modules, éléments de modules, et modalités d’évaluation. Chaque entité a des caractéristiques spécifiques et des relations entre elles qui sont décrites ci-dessous.

### 1. Professeur
Les professeurs sont les utilisateurs responsables de la gestion des éléments de module et de la saisie des notes des étudiants.

**Attributs d'un Professeur :**
- `id`: Identifiant unique du professeur.
- `nom`: Nom du professeur.
- `prenom`: Prénom du professeur.
- `specialite`: Spécialité ou domaine d'enseignement du professeur (ex : Mathématiques, Informatique, etc.).
- `code`: Code unique pour identifier le professeur (par exemple, un code de membre ou ID interne).

**Gestion :**
- **Ajout** : Un administrateur peut ajouter un professeur via un formulaire d'inscription, en renseignant les informations nécessaires.
- **Modification** : Le profil du professeur peut être modifié par un administrateur (par exemple, changer la spécialité).
- **Suppression** : Un administrateur peut supprimer un professeur si nécessaire.
- **Affectation** : Un professeur peut être affecté à un ou plusieurs éléments de modules dans différents semestres et filières.

### 2. Filière
Une filière représente une spécialisation dans laquelle des modules sont organisés pour des étudiants (par exemple, Informatique, Mathématiques, etc.).

**Attributs d'une Filière :**
- `id`: Identifiant unique de la filière.
- `nom`: Nom de la filière (ex : Informatique, Génie Civil, etc.).

**Gestion :**
- **Ajout** : L’administrateur peut ajouter une nouvelle filière en précisant son nom.
- **Modification** : L’administrateur peut modifier le nom d’une filière existante.
- **Suppression** : L’administrateur peut supprimer une filière, mais cela doit être fait avec précaution pour éviter la suppression accidentelle de modules ou d’éléments de module associés.

### 3. Module
Les modules sont les cours ou sujets d'étude proposés dans une filière donnée. Un module appartient à une filière et est organisé par semestre.

**Attributs d'un Module :**
- `id`: Identifiant unique du module.
- `code`: Code unique du module (ex : MAT101, INFO202, etc.).
- `nom`: Nom du module (ex : "Algèbre", "Programmation Java").
- `filiereId`: Identifiant de la filière à laquelle le module appartient.
- `semestre`: Semestre auquel le module appartient (S1 à S5).

**Gestion :**
- **Ajout** : L’administrateur peut ajouter un module en spécifiant son code, son nom, la filière et le semestre.
- **Modification** : L’administrateur peut modifier le nom du module, la filière ou le semestre si nécessaire.
- **Suppression** : L’administrateur peut supprimer un module, mais il doit s’assurer qu'aucun élément de module n’est affecté avant de supprimer un module.

### 4. Élément de Module
Les éléments de modules sont des sous-parties ou unités d'un module, tels que des évaluations spécifiques (ex : examen final, TP, projet).

**Attributs d'un Élément de Module :**
- `id`: Identifiant unique de l'élément de module.
- `moduleId`: Identifiant du module auquel cet élément appartient.
- `nom`: Nom de l'élément de module (ex : "Examen final", "TP de programmation").
- `coefficient`: Coefficient de l'élément dans le calcul de la moyenne du module (ex : 50%, 30%, etc.).

**Gestion :**
- **Ajout** : Un administrateur peut ajouter un élément de module à un module existant en définissant son nom et son coefficient.
- **Modification** : Un administrateur peut modifier un élément de module, par exemple, changer son nom ou son coefficient.
- **Suppression** : L’administrateur peut supprimer un élément de module, ce qui affectera la structure de la note du module.

### 5. Modalité d'Évaluation
Les modalités d’évaluation définissent les types d’évaluations pour chaque élément de module (ex : Contrôle Continu, TP, Projet).

**Attributs d'une Modalité d'Évaluation :**
- `id`: Identifiant unique de la modalité.
- `elementId`: Identifiant de l'élément de module auquel cette modalité appartient.
- `type`: Type de la modalité (ex : "CC", "TP", "Projet").
- `coefficient`: Coefficient de la modalité dans le calcul de la note de l'élément de module.

**Gestion :**
- **Ajout** : L’administrateur ou le professeur peut ajouter des modalités d’évaluation à un élément de module. Chaque élément peut avoir plusieurs modalités.
- **Modification** : L’administrateur ou le professeur peut modifier les modalités d’évaluation, y compris leur coefficient.
- **Suppression** : L’administrateur peut supprimer une modalité d’évaluation si elle n'est plus pertinente ou a été ajoutée par erreur.

## Relations entre les entités
- Un professeur peut être affecté à plusieurs éléments de module.
- Un module appartient à une filière et peut être associé à plusieurs éléments de module.
- Un élément de module appartient à un module et peut avoir plusieurs modalités d’évaluation.
- Chaque modalité d’évaluation est liée à un élément de module spécifique.
 
