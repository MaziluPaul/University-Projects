;7.z=(5*a-b/7)/(3/b+a*a)

ASSUME CS: code, DS: data

data SEGMENT

a db 2
b db 3;7
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
; CX = a*a

mov AL, 3
idiv b
; AX = 3/b
mov AH, 0
add AX, CX
; AX = 3/b+a*a
mov CX, AX
; CX = 3/b+a*a

mov AL, b
cbw
cwd
mov BX, 7
idiv BX
; AX = b/7

mov BX, AX

mov AL, 5
imul a

sub AX, BX
; AX = 5*a-b/7
cwd
idiv CX

mov rez, AX
mov AX, 4C00h
int 21h

code ENDS
END start