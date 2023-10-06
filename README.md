# HEH Gourmet

## Présentation
Dans le cadre de ce projet, après avoir identifié des inefficiences dans le système de commande de
repas à la HEH, nous avons décidé de créer un site de commande en ligne. Ce site permettra aux
utilisateurs d'accéder au menu proposé, de passer des commandes, de personnalisées et de payer la
commande. Les utilisateurs auront également la possibilité de créer un compte client, facilitant la
répétition de commandes quotidiennes, l’accès à l’historique des commandes et d’avoir des
réductions ou offres de fidélité proposées par le gérant. Du côté du gérant, le site lui permettra de
modifier le menu, d'accéder à la liste des commandes et d’annuler des commandes en cas de
manque de produits, entraînant ainsi un remboursement.

## Détails du Projet
### analyse
### Fonctionnalité
#### Utilisateur
##### Tout Utilisateur
- lister les produits disponibles
- suivis de l'état de la commande
- payement via PayPal
- possibilité d'annuler une commande X h avant quel soit préparée

##### Utilisateur connecté
- connection avec possibilité de multifacteur
- commande avec compte permettant plus de fonctionnalité telle que des point de fidélité etc.
- commande récurrente
- point de fidélité
- panier lié au compte

##### Utilisateur déconnecté
- commande sans compte requirent uniquement une adresse email. un mail avec le numéro de commande sera donc envoyé par mails une fois la transaction effectuée.
- panier stocké dans les cookies du navigateur

#### Opérateur
- connection multifacteur obligatoire
- permets de voir la liste des commandes à préparer 
- possibilité d'annuler une commande avec un remboursement automatique en cas de problème ( manque de produit, etc.)
- validation d'une commande ( la commande a bien été délivrée )
- permets de configurer le nombre de produits disponibles

#### Administrateur
- connection multifacteur obligatoire
- possibilité de gérer les produits ( ajouter, retirer, éditer)
- possibilité de gérer les commandes ( ajouter, retirer, éditer)

### Technologie 
#### En Front End :
- React
- Tailwind CSS

#### En Back End :
- SPRING boot ( + jakarta + lombok )
