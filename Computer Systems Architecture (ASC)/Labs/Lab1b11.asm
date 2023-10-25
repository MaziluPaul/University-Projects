;11. z=(5*a-b/7)/(3/b+a*a).

ASSUME CS:code, DS:data

data SEGMENT

a db 1
b db 1
rez dw ?

data ENDS

code SEGMENT
start:
mov AX, data
mov DS, AX

mov AL, a
imul a
; AX = a*a
mov CX, AX

mov AL,3
idiv b
; AX = 3/b
mov AH, 0

add AX, CX
; AX = 3/b+a*a
mov CX, AX

mov AL, b
mov BL, 7
idiv BL
; AX = b/7
mov AH, 0
mov BX, AX

mov AL, 5
mul a
; AX = 5*a

sub AX, BX
; Ax = 5*a-b/7
cwd
idiv CX
; AX = (5*a-b/7)/(3/b+a*a)
mov rez, AX
mov AX, 4C00h
int 21h
code ENDS
END start