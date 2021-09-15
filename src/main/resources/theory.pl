/**
 * S: The starting story_node id.
 * E: The ending story_node id.
 * L: A list containing all the story_nodes id crossed from S to E.
 * path(+S, +E, -L)
 */

% Match the starting story_node using S.
path(S, E, L) :- story_node(S, _, P),
                        path(S, E, P, L).

% Base case. E is found in the first pathway.
path(S, E, [pathway(E, _)|_], [S, E]).
% E is not in the first pathway, iterate through the other pathways of the same node.
path(S, E, [pathway(ID, _)|RP], L) :- path(S, E, RP, L).
% E is not in the first pathway. Search E in the pathways of ID.
path(S, E, [pathway(ID, _)|_], [S|L]) :- path(ID, E, L).
